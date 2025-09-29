#!/bin/bash
HOME="$(getent passwd "${SUDO_USER:-}" | cut -d: -f6)"
USER="$(getent passwd "${SUDO_USER:-}" | cut -d: -f1)"

function verifyFiles() {
    local files=("$@")
    for fileName in "${files[@]}"; do
        if [ ! -e $fileName ]; then
            echo "Necessary file ${fileName} not found"
            exit 1
        fi
    done
}

ROOT_DIR=$PWD
BUILD_DIR="${PWD}/dist"
SCRIPTS_DIR="${PWD}/scripts"
NECESSARY_FILES=("${ROOT_DIR}/scripts" "${ROOT_DIR}/scripts/install.sh" "${SCRIPTS_DIR}/annoyingstudyterm.sh")

# Checks if the script is being run as sudo
if [ "$EUID" -ne 0 ]; then
    echo "Please run the build script as sudo"
    exit 1
fi
# Checks if the script is being run from the right place
if [ ! -e "${ROOT_DIR}/scripts" ]; then
    echo "Please run the build script from the root of the project"
    exit 1
fi

verifyFiles "${NECESSARY_FILES[@]}"

# Checks if it is ok to override the existing directory
if [ -e $BUILD_DIR ]; then 
    read -p "Build directory already exists at ${BUILD_DIR}. Should override it? (y/n) " override
    if [ $override != "y" ]; then
        exit 0
    else
        echo "Recreating build directory"
        rm -rf $BUILD_DIR
    fi
fi

sudo -u $USER mvn clean package
mkdir $BUILD_DIR
cp "${SCRIPTS_DIR}/install.sh" "${BUILD_DIR}/install.sh"
cp "${SCRIPTS_DIR}/annoyingstudyterm.sh" "${BUILD_DIR}/annoyingstudyterm.sh"
cp "${ROOT_DIR}/target/annoyingstudyterm.jar" "${BUILD_DIR}/annoyingstudyterm.jar"