from textblob import Word
import sys

string_tocheck = Word(sys.argv[1])
print string_tocheck.spellcheck()[0][0]
