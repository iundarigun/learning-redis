# REDIS
This repo is the result of studing Redis. See `references` section to understand better the path follow.

## Install
To install redis, we can go to the official site: https://redis.io/download

Or we can start them in docker:
```
$ docker run --name local-redis -p 6379:6379 -d redis
```
To run CLI we can do:
```
$ docker exec -it local-redis redis-cli
```

## CLI basic commands

To create a key, we can execute:
```
127.0.0.1:6379> SET "last_user" "iundarigun"
OK
```

To get a key, we can execute:
```
127.0.0.1:6379> GET "last_user"
"iundarigun"
```

We can append string into an existing key:
```
127.0.0.1:6379> APPEND "last_user" " additional"
```

To delete a key, we can execute:
```
127.0.0.1:6379> DEL "last_user"
(integer) 1
```

List all keys:
```
127.0.0.1:6379> KEYS *
1) "last_user"
```

It is a good pattern structure your key with items that make sanse for you and separate by `:`. Setting a key structure:
```
127.0.0.1:6379> SET "result:2019-10-29:test-coverage" "100%"
OK
```

Setting more than key at the same time:
```
127.0.0.1:6379> MSET "result:2019-10-28:test-coverage" "98%" "result:2019-10-27:test-coverage" "93%" "result:2019-10-26:test-coverage" "95%"
OK
```

Get keys from results of 2019:
```
127.0.0.1:6379> KEYS "result:2019-*"
```

Get keys from results of month 10, 11, or 12 from 2019:
```
127.0.0.1:6379> KEYS "result:2019-1?-*"
```

To search with some conditions at the same time. Get keys from results of month 10 and 11 from 2019, but not 12:
```
127.0.0.1:6379> KEYS "result:2019-1[01]-*"
```

Saving more info in the same key, using a set of values:
```
127.0.0.1:6379> HSET "result:2019-11-01:test-coverage" "coverage" "97%"
127.0.0.1:6379> HSET "result:2019-11-01:test-coverage" "exclude package" "br.com.devcave.*.domain.*"
```

To set some values at the same time:
```
127.0.0.1:6379> HMSET "result:2019-11-02:test-coverage" "coverage" "94%" "exclude package" "br.com.devcave.*.domain.*"
```

To get an especific value:
```
127.0.0.1:6379> HGET "result:2019-11-01:test-coverage" "exclude package"
```

To get all values of a set in a key:
```
127.0.0.1:6379> HGETALL "result:2019-11-01:test-coverage"
```

To get all values without key:
```
127.0.0.1:6379> HVALS "result:2019-11-01:test-coverage"
```

To remove an element of a set:
```
127.0.0.1:6379> HDEL "result:2019-11-01:test-coverage" "exclude package"
```
### Using TTL

Setting a ttl. **Expire is in seconds**:
```
127.0.0.1:6379> HMSET "session:user:2110" "name" "Oriol" "last name" "Canalias"
127.0.0.1:6379> EXPIRE "session:user:2110" 60
```

To view the expire time left:
```
127.0.0.1:6379> TTL "session:user:2110"
```

### Using NX and XX

When set a key, if we use `NX` modifier, the valeu is only set if key doesn't exists:
```
127.0.0.1:6379> SET "session:user:860" "iundarigun" NX
```
When set a key, if we use `XX` modifier, the valeu is only set if key exists:
```
127.0.0.1:6379> SET "session:user:860" "iundarigun" XX
```

### Using to count

We can use a value to count or some a value. If the key is not exists, redis create it:
- Increment (unit):
    ```
    127.0.0.1:6379> INCR page:/home/2019-10-28
    ```
- Increment with value:
    ```
    127.0.0.1:6379> INCRBY page:/home/2019-10-28 10
    127.0.0.1:6379> INCRBYFLOAT page:/home/2019-10-28 10.5
    ```
- Decrement:
    ```
    127.0.0.1:6379> DECR page:/home/2019-10-28
    ```
- Decrement with value:
    ```
    127.0.0.1:6379> DECRBY page:/home/2019-10-28 10
    127.0.0.1:6379> DECRBYFLOAT page:/home/2019-10-28 10.5
    ```

### Saving bits

To save bits, we can save a group of bits (true/false) for a especific key. The function is similar to HSET/HGET, but the id of the set (not confunding with the key of redis) is always a number

```
127.0.0.1:6379> SETBIT enter:2019-10-28 9945 1
```
To get:
```
127.0.0.1:6379> GETBIT enter:2019-10-28 9945
```
To count bits(number of trues) in a set:
```
127.0.0.1:6379> BITCOUNT enter:2019-10-28
```
Repeat true ids on some keys, need some and store the result in another key:
```
127.0.0.1:6379> BITOP AND enter:resume enter:2019-10-30 enter:2019-10-31
```
Count if existes some true ids on some keys, need some and store the result in another key:
```
127.0.0.1:6379> BITOP OR enter:resume enter:2019-10-30 enter:2019-10-31
```

### Working with lists

To create a list with the last elements of some list, like a list of the last news, we can use `LPUSH` to put elements in a list. If we can to delimited the list to a finit number of elements we can use the `LTRIM` instruction.
Other instructions that you can use are `LINDEX` to get some element passing position and `LLEN` to get the size of the list.
```
127.0.0.1:6379> LPUSH "last_news" "new of 2019-10-30 10:00"
...
127.0.0.1:6379> LINDEX "last_news" 0
127.0.0.1:6379> LLEN "last_news"
...
127.0.0.1:6379> LTRIM "last_news" 0 2
```
We can get some elements in a range:
```
127.0.0.1:6379> LRANGE "last_news" 1 2
```
If we need to put some elements at the end of the list, we can use `RPUSH`
```
127.0.0.1:6379> RPUSH "last_news" "new of 2019-10-30 09:00"
```

### Using list like a queue
We can use this kindle of list like a queue, using `RPUSH`. To get the elements to the queue "deleting" the element, we can use `LPOP`:
```
127.0.0.1:6379> RPUSH "queue:email-confirmation" "user1@devcave.com.br"
127.0.0.1:6379> LPOP "queue:email-confirmation"
```
To implement a LIFO, we can use `RPOP` instruction.

If the queue is empty, we can block the pop for a time, expressed in seconds(0 to wait forever):
```
127.0.0.1:6379> BLPOP "queue:email-confirmation" 30 
127.0.0.1:6379> BRPOP "queue:email-confirmation" 30 
```

### Collections
A collection has a list of unic elements inside. To add elements:
```
127.0.0.1:6379> SADD "friends:iundarigun" "friend1" "friend2" 
```
Counting elements:
```
127.0.0.1:6379> SCARD "friends:iundarigun"
```
Showing elements:
```
127.0.0.1:6379> SMEMBERS "friends:iundarigun"
```
Validating existing elements:
```
127.0.0.1:6379> SISMEMBER "friends:iundarigun" "friend2"
```
Removing:
```
127.0.0.1:6379> SREM "friends:iundarigun" "friend1"
```
If we want to get a intersection of two collections:
```
127.0.0.1:6379> SINTER "friends:iundarigun" "friends:iunda"
```
To get differences: 
```
127.0.0.1:6379> SDIFF "friends:iundarigun" "friends:iunda"
```
Union of two collections without repetions:
```
127.0.0.1:6379> SUNION "friends:iundarigun" "friends:iunda"
```

### Making a ranking

To create a ranking, we can use a hash key to declare the players:
```
127.0.0.1:6379> HSET "players:id:991" "username" "iundarigun" "points" 12 
```
If we want to get all informations, we can use `HGETALL`, like we see before. If we want increase the points of the player, we can use some instructions, `HINCRBY` or `HINCRBYFLOAT`. Doesn't exists `HINCRBY`!
```
127.0.0.1:6379> HINCRBY "players:id:991" "points" 3 
```

To create an order list, we can use `zset` (Sorted Set). To add values in a key, we use `ZADD` with key, value used to order and identifyer of the element:
```
127.0.0.1:6379> ZADD "players:ranking" 15 "player1" 
```
We count the elements using:
```
127.0.0.1:6379> ZCARD "players:ranking" 
```
We can get the firsts members by order using (asc):
```
127.0.0.1:6379> ZRANGE "players:ranking" 0 3 WITHSCORES
```
We can get descending order:
```
127.0.0.1:6379> ZREVRANGE "players:ranking" 0 3 WITHSCORES
```
To get all elements, we can use negative positions:
```
127.0.0.1:6379> ZREVRANGE "players:ranking" 0 -1 WITHSCORES
```
To get score from some elements, or the positions, we can use:
```
127.0.0.1:6379> ZSCORE "players:ranking" "player1"
127.0.0.1:6379> ZRANK "players:ranking" "player1"
127.0.0.1:6379> ZREVRANK "players:ranking" "player1"
```
To increase points:
```
127.0.0.1:6379> ZINCRBY "players:ranking" 5000 "player1"
```

### Cloud redis
app.redislabs.com is a cloud redis. We can create an account to play.


## Spring and Redis

We can use redis for a few thinks with Spring-boot. In this repo, there is a folder `redis` with Kotlin and Spring boot application with the next examples:
- Use RedisTemplate to get/set values - ProgrammerRepositoryImpl.kt
- Use Spring-Data to manage crud operations - StudentRepository.kt
- Using as a cache - Sample in CacheService.kt
- Tests with embedded server - RedisApplicationTests.kt


### References
- [Redis I](https://www.alura.com.br/curso-online-nosql-chave-valor-com-redis-1) and [Redis II]() on alura.com.br
- [Learn Redis and Use Jedis with Spring Data Redis](https://www.udemy.com/course/learn-redis-and-utilize-jedis-with-spring-data-redis/) on udemy.com
- Post [Redis as a cache](https://programmerfriend.com/ultimate-guide-to-redis-cache-with-spring-boot-2-and-spring-data-redis/)
- Post [Spring Redis Tutorial](https://www.baeldung.com/spring-data-redis-tutorial) from baeldung.com
- Post [Embedded Redis Server](https://www.baeldung.com/spring-embedded-redis) form baeldung.com
- Lib [Redis embedded server](https://github.com/kstyrc/embedded-redis) on github.com