local lockKey = KEYS[1]
local lockValue = KEYS[2]

-- 上面是传入的2个参数
-- 执行 SETNX
local result_1 = redis.call('SETNX', lockKey, lockValue)
-- 如果==true  就执行下面的方法 SETEX
if result_1 == true
then
--执行 SETEX
local result_2= redis.call('SETEX', lockKey,3600, lockValue)
--  返回 true
return result_1
else
-- 如果失败 就直接返回  false
return result_1
end

