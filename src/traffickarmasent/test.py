import tweepy

import json,re,sys



# Authentication details. To  obtain these visit dev.twitter.com

consumer_key = 'ufulV3imKoYNzdh58LotTC1YD'

consumer_secret = '2A781ma736HTenAXXYn9tRIelQYJkbCqY0GLi7W71ZwwDmNU59'

access_token = '2564905075-MY9osfHabaRnonQVHHhHeA1vCLSOhuHWjBNBiIY'

access_token_secret = 'JsD8Woc7iiFiDSwoCwjNAb6KNEurz7tBqSj9pJV8WXabr'



# This is the listener, resposible for receiving data
'''
class StdOutListener(tweepy.StreamListener):

    def on_data(self, data):

        # Twitter returns data in JSON format - we need to decode it first

        decoded = json.loads(data)
		element  = decoded['text']
	    print element
		
        #print '@%s Name %s ID %s:TWID %s  %s' % (decoded['user']['screen_name'],decoded['user']['name'], decoded['user']['id'],decoded['id'] ,decoded['text'].encode('ascii', 'ignore'))

        print ''

        return True



    def on_error(self, status):

        print status



if __name__ == '__main__':

    l = StdOutListener()

    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)

    auth.set_access_token(access_token, access_token_secret)



    print "Showing all new tweets for #programming:"



    # There are different kinds of streams: public stream, user stream, multi-user streams

    # In this example follow #programming tag

    # For more details refer to https://dev.twitter.com/docs/streaming-apis

    stream = tweepy.Stream(auth, l)

    stream.filter(track=['games'])
'''
class StdOutListener(tweepy.StreamListener):
    def on_data(self, data):
        # Twitter returns data in JSON format - we need to decode it first
        decoded = json.loads(data)

        # Also, we convert UTF-8 to ASCII ignoring all bad characters sent by users
        #print '@%s: %s' % (decoded['user']['screen_name'], decoded['text'].encode('ascii', 'ignore'))
        element = decoded['text']
        print element , 
        element = re.sub(r"htt\S+", "", element)
        element = re.sub("@(\w+)| #","",element)
        #element = re.sub("(@[A-Za-z0-9]+)","",element)
        #element = ' '.join(re.sub("(@[A-Za-z0-9]+)|([^0-9A-Za-z \t])"," ",element).split())
        print element
        return True

    def on_error(self, status):
        print status

if __name__ == '__main__':
    l = StdOutListener()
    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)

    print "Showing all new tweets for #programming:"

    # There are different kinds of streams: public stream, user stream, multi-user streams
    # In this example follow #programming tag
    # For more details refer to https://dev.twitter.com/docs/streaming-apis
    stream = tweepy.Stream(auth, l)
    stream.filter(track=['ping'])
