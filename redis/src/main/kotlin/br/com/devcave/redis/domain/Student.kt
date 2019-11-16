package br.com.devcave.redis.domain

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("Student")
class Student(val id: String, val name: String, val age: Int) : Serializable