#!/bin/bash
HOME="$(getent passwd "${SUDO_USER:-}" | cut -d: -f6)"
USER="$(getent passwd "${SUDO_USER:-}" | cut -d: -f1)"

if [ "$EUID" -ne 0 ]; then
    echo "Please run the installation script in using \"sudo\""
    exit 1
fi

if [ ! -e "${PWD}/annoyingstudyterm.jar" ]; then
    echo "Please run the installation script in the same directory where the resources are located"
    exit 1
fi

mkdir -p /usr/local/lib/annoyingstudyterm
    cp "${PWD}/annoyingstudyterm.jar" "/usr/local/lib/annoyingstudyterm/annoyingstudyterm.jar"
chmod 755 "${PWD}/annoyingstudyterm.sh"
    cp "${PWD}/annoyingstudyterm.sh" "/usr/local/bin/annoyingstudyterm"

LINEPOS="$(cat ~/.profile | grep -n "annoyingstudyterm quiz")"
LINE="$(echo $LINEPOS | cut -d ":" -f 1)"
if [ "$LINE" ==  "" ]; then
    echo "annoyingstudyterm quiz" >> ${HOME}/.profile
else
    echo "[WARNING] Start command already found in "${HOME}/.profile" skipping this part."
fi
sudo -u "$USER" mkdir -p ${HOME}/.local/annoyingstudyterm
sudo -u "$USER" mkdir -p ${HOME}/.config/annoyingstudyterm