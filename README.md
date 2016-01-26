# Overview of solution
****
This is my implementation for the address cache. I created a main method in **addressCacheMain** to show off the functionality. When the main is run it populates the address cache and then allows the user to input commands. The caching time is set to 60 seconds by default, but this can be changed when you create a new cache object.
Also included is a test class(**AddressCacheImplementorTest**) for the methods in **AddressCacheImplementor**.

Inside of **AddressCacheImplementor** I created an inner class called **Address**. The purpose of this class is to be a wrapper for InetAddress and also keep track of the time that the address has been in the cache. I found it simpler that each address should be responsible for knowing its cached time and then the cache is responsible for ejecting it if it exceeds the max time.

The cache is an implementation of LinkedList, since the solution needed to be designed to optimal asymptotic performance. In this situation a Linked list is more desirable over an Array List because additons and deletions can be done in constant time. Where as with an array list they are done in linear time because all later elements need to be relocated. 

# Running the Program
****
To test the address cache implementation in real time run the **addressCacheMain** class. When this class runs it populates the cache with 50 addresses with a cached time of 60 seconds. The user can then enter in values to add and delete address. the commands are as follows...
- 1 - remove()
- 2 - peek()
- 3 - take()
- 4 - size()
- 5 - isEmpty()
- 6 - close()
- 7 - repopulate
- 8 - offer()
- 9 - Stop Program

When you choose offer() it will generate a random address and add it to the cache. The two methods you can not run here are **remove(address)** and **contains(address)**, but those are covered in the test class. as mentioned above the cache is set to 60 seconds but that can be changed. So after 1 minute addresses will be ejected from the cache but if you hit 7 it will repopulate with 50 new addresses. 


# Methods & Asymptotic Complexity
****
**AddressCacheImplementor( caching_time )**
- constructor takes in the number of seconds that addresses are cached for, it takes this value in as an int.

**offer( address )**
 - This method takes O(n) time because it first needs to search if the address in the queue. if it is in the queue than it will get the current index and delete it (constant time) and then add the new occurence to the front of the list (also constant time).
 - The method will not accept any new address if the cache has been 'closed'

**contains( address )**
- this method runs in O(n) time because it needs to search through the entire list to find the matching address.

**remove( address )**
- this methods runs in O(n) time. This is because it must do an initial linear search through the list until it finds the matching address. once found it can delete the reference. 


**peek()**
- this method runs in constant time because it simply gets the first element.

**remove()**
- this method runs in constant time. It will delete the reference to the first address and the head node will now point to the next address

**take()**
 - this method runs in constant time for the same reason as *remove()*
 
**close()**
 - this method runs in O(n) time (surprisingly). I assumed it would run in constant time by removing reference from the head node. But suns implementation of clear() loops through the list and sets all elements to null. The reason it does this is because of garbage collection.
 
**size()**
- this method runs in constant time because LinkedList keep track of their element count

**isEmpty()**
- this method runs in constant time.

**incrementCachedTime()**
- This is the method that keeps track of the time that all elements have been in the cache. the documentation seems to specify that the clean up task should run every 5 seconds, however I found it more efficient(and more functional) to evict elements from the cache as they are incremented. 
- So the method increments every second. it will check the amount of time that address has been in the cache. If it is greater than the max caching time(which is passed into the cache constructor) it will evict it. 
- The method traverses the cache backwards so that the oldest elements are the first to be ejected.
- This method runs in O(n) time because it needs to incremnet every address in the cache. 
