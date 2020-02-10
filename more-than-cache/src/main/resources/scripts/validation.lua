
local members = redis.call('smembers', KEYS[1])
for mem =1,#members do
    if redis.call('EXISTS', KEYS[2]..':'..members[mem]) ~= 1 then
        redis.call('SREM', KEYS[1], members[mem])
    end
end
return redis.call('smembers', KEYS[1])