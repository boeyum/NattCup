# "NattCup" - a social tournament

NattCup was first attempted in the early 1990s as a team handball tournament designed to foster unity both within and between teams. The tournament was originally played between 8 p.m. and 8 a.m.—hence the Norwegian name "NattCup," meaning "Night Cup." Naturally, the tournament can also be played at other times and with a different duration.
<br><br>
The tournament is unique in that it does not rely on the usual points and goal difference. Instead, the focus is on the total amount of playing time a team accumulates over the course of the tournament. If two teams are tied on playing time, the winner is the team that has scored the most goals. The rules are explained in the "userman.pdf" file.<br><br>

## NattCup software

The software designed to run the tournament was developed using Java and Swing. It is structured as a Maven project, with the IntelliJ development platform used for its creation. The included .pom file contains everything necessary to generate an executable .jar file for the Project in IntelliJ.<br><br>
The software includes built-in functionality for setting up and running the tournament. The entire set of tournament rules is integrated into the software. Additionally, there is a feature for making emergency edits to a team's data should the need arise.<br><br>
The software can be configured to run in either Norwegian or English. The default setting from GitHub is Norwegian. To switch the software to English, replace "textconfig.json", "userman.pdf", and "rules.html" in the config directory with "en_textconfig.json", "en_userman.pdf", and "en_rules.html" from the language directory.<br><br>
The software can easily be translated into languages ​​other than those included with the source (English and Norwegian). This is done by translating the file starting with "en_textconfig.json" in the language directory and replacing "textconfig.json" in the config directory with this file. **Note: Do not change the text under "id" in the JSON file, as doing so will cause the software to crash.**<br><br>
## DOKUMENTATION
Documentation regarding the use and setup of the software is included with the source code in "userman.pdf".
<br><br>

# Change log

**2.1.22** First Version made public


# Licens
[MIT](https://github.com)

