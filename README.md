# ROUTES
Below are accessible routes and what they return.

_/api/feed/{user.id}_         - Returns up to top 100 Recent Tweets for
													    - Selected user

_/timeline/{user.username}_   - Redirects to "index.jsp", a wall of
			    a give users Tweets. If logged in, One
			    can follow them on that timeline page.

_/login.jsp_ 		    - Login with user password
			    (Mock LDAP stored in local MYSQL Database)
			    - Can switch sessions

_/feed.jsp_		    - If Logged In, will show Home Page,
			    - which has 100 most Recent
			    - Tweets by those logged in user are following.


# DESIGN
In designing a Twitter-like internal service, the emphasis
was to be on Scalability & Speed:

**Scalability**
	- With 10K users, and Intuit still growing quickly,
	  more resources and services can be expected to exist
	- A decoupled back-end and flexibly designed
	  REST services would allow for this
	- Front-End Components are modularized and independent
	- Back-End Database Access functions need to be decoupled
	  for REST and Servlet Logic

**Speed**
	- Home Feed requires the largest amount of calculations,
	  and assumptions needed to be made on what users wanted to see.
		Assumption #1: Users want to be notified of the latest Tweets from who they follow
		Assumption #2: Users want variety in their home Feed, so tweets from different users

	- Decision was made to trade memory for speed, with 10K
	  users worth of data, and tweets store 144 characters,
	  so enough space for trade-off.  

	- Each User is subscribed to followers, and receive push
	   "Tweets" to their Redis
 	  instances, which store them in queue fashion

	- Representation of data under the hood is linkedlist, which
	  means we can optionally keep X amount of tweets per home
	  feed to save space.  Twitter stores up to 800 tweets for
          home feed, Intuit can opt to store less to save memory.

	- Overall, this allows for a fast READ operation,
	  for a better user experience and less complexity and logic,
	  which also points towards scalability as
	  services become more complex.

# MISC.
I. Persistent Data Storage was in MYSQL, In-Memory Cache with local Redis database.

II. Future Improvements:
- Create more REST resources
- Decouple database-access and back-end logic to allow front-end to consume REST APIs

- Store Tweets in Redis Cache as JSON, including all necessary metadata
- Handle Exceptions
- Log Data
- Use Spring Framework

@Signed:  JesseRen 2018
