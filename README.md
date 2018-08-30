# quiz-composer-dates

This is a private project (although I am not trying to _forbid_ anyone from contributing).

I was an Eclipse user long ago. I wanted to get up-to-date with using Eclipse (Java) with Maven and git.
So this is a little play project.

It will read a database of composer names and dates and quizes the user. It's at v0.1, the first
working version.

It is moderately object oriented.

The CSV file has some composers already marked as Memorized, for test purposes. If this were for
release, such markings would be down to the user and/or would be created during the game.

If the CSV file is edited, one has to be VERY CAREFUL. Editing in Excel, for example, typically
won't work because Excel CSV export is very bad at handling encodings, and it's very easy to
lose all the accented letters. I use a good text editor like Notepad++ or OpenOffice. I think
I exported the CSV from OpenOffice using UTF8.