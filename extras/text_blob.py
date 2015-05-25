from textblob import TextBlob
import sys

string_tocheck = TextBlob(sys.argv[1])
print string_tocheck.correct()
