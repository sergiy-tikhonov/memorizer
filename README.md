# memorizer

About app:
- Helps to memorise themes that user wants to learn in a form of questions/answers. 
- User should fill questions and answers in Google Docs files. For now there is no option to edit questions/answers within app.
- Questions are grouped in question sets - dictionaries. So each Google Docs file is a dictionary in app, each dictionary holds a set of questions and answers.
- User should select dictionary and after that - to train his questions/answers. User should mark his knowledge of right answer by himself (with up to 5 stars) 

Each dictionary can be one of two types: 
- words (pairs of short text values. For example - word in English and corresponding word in Spanish). There is a predefined structure of acceptable Google Docs file - a table (tables) with 4 columns (first - number of question - should be unique, second - question, third - answer, fourth - date of adding the question)
- text (as usual - short question and long answer. For example, question "What were main reasons of World War II?" and full answer). There is a predefined strucure of acceptable Google Docs file. Each question - is a separate table with 6 rows (however, not all of them are processed for now). First row - title of the question. Second - full answer text. Third - text tag/tags. Fourth - http link(links) to a sources of answer. Fifth - date of adding the question. Sixth - unique question id. 

Minimal SDK: 26

Used technologies:
- Kotlin
- MVVM/LiveData for a presentation layer
- Dagger Hilt for DI
- Repository/Datasource pattern
- Coroutines for async/background
- Jetpack Navigation Component for navigation (Single Activity pattern)
- Room for data persistence
- Google Drive API, Google Docs REST API for accesing Google Docs files
