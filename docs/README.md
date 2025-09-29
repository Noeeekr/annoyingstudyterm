Annoyingstudyterm
    
    Program description: 
        Runs an annoying terminal script every 24h with random custom questions that you made.
        Asks what was the last thing you studied to add to
        
    Behavior:
        Starts a quiz when the terminal profile is loaded if it wasn't done at least once in the last 24h
        Show other commands help when called in command line with no argument  
        
        Commands: 
            help --help -h:     Shows other commands help. 
            start:              Starts a random quiz.
            list:               Shows a list with all questions.
                --format:       Showscase all the question formats.
                --with-answers: Shows the answers too.
            add:                Starts the prompt to add a new question.
