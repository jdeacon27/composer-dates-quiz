# quiz-composer-dates

This is a private project (although I am not trying to _forbid_ anyone from contributing).

I was an Eclipse user long ago. I wanted to get up-to-date with using Eclipse (Java) with Maven and git.
So this is a little play project.

It will read a database of composer names and dates and then quizz the user for the composer's dates.
It's reached the point where it's runnable working from an unsophisticated GUI. If you get both
the birth and death dates correct, you'll be told so. If you omit either or both dates, or
get any dates wrong, you will be shown the correct dates. Use the Tab and Enter keys or the
mouse.

It is moderately object oriented.

The CSV file has the hundred or so composers that I've heard of. I have a bigger file that I
haven't bothered to put here. But if anyone asks, I can.

The CSV file has some composers already marked as _Memorized_, for test purposes. If this were for
release, such markings would be down to the user and/or would be created during the game.

There are two "name" fields in the CSV. The first has the family name first followed by all
the forenames. The second name field has the forenames first. For most of those composers marked
 as _Memorized_, I've edited the second name fields' forenames to match common usage (per 
 Wikipedia). Mozart for example doesn't have all the registry names or all the names that
 Leopold thought were in the registry. Instead he is Wolfgang Amadeus Mozart.

If the CSV file is edited, one has to be *VERY CAREFUL*. Editing in Excel, for example, typically
won't work because Excel's CSV export is very bad at handling encodings, and it's very easy to
lose all the accented letters. I use a good text editor like Notepad++, or OpenOffice. I think
I exported the CSV from OpenOffice using it's default encoding - Western Europe(Windows-1252).

There is a jar file in addition to the sources. It doesn't include the database (the CSV file).
I'm not even sure if it could. Anyway, you'd need to download the CSV with the jar file.
