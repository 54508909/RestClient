package com.liao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/** 
 *  
 * 基于spring和redis的redisTemplate工具类 
 * 针对所有的hash 都是以h开头的方法 
 * 针对所有的Set 都是以s开头的方法                    不含通用方法 
 * 针对所有的List 都是以l开头的方法 
 */

@Component
public class RedisUtils {  
	@Autowired
	private JedisPool jedisPool;

    /*获取Jedis资源*/
    public Jedis getJedis() {
        return jedisPool.getResource();
    }
    
    /*释放Jedis连接*/
    public void close(Jedis jedis) {
        if(jedis!=null) {
            jedis.close();
        }
    }


	//=============================common============================  
   
      
    /** 
     * 判断key是否存在 
     * @param key 键 
     * @return true 存在 false不存在 
     */  
    public boolean hasKey(String key){ 
    	Jedis client = getJedis();
        try {  
            return client.exists(key);
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }finally {
        	close(client);
		}
    }  
      
    /** 
     * 删除缓存 
     * @param key 可以传一个值 或多个 
     */  
    public void del(String ... key){  
    	Jedis client = getJedis();
		try{
			if(key!=null&&key.length>0){  
		        if(key.length==1){  
		        	client.del(key[0]);  
		        }else{
		        	client.del(key);
		        }  
		    }  
		} catch (Exception e) {  
		    e.printStackTrace();  
		}finally {
			close(client);
		}
        
    }  
    
    /**
     * 模糊删除
     * @param key
     */
    public void delDataFuzzy(String key){
    	Jedis client = getJedis();
    	try{
    		Set<String> keysList= client.keys(key);
        	String[] kesArr = (String[]) keysList.toArray();
        	client.del(kesArr);
    	} catch (Exception e) {  
		    e.printStackTrace();  
		}finally {
			close(client);
		}
    	
    }
      
    //============================String=============================  
    /** 
     * 普通缓存获取 
     * @param key 键 
     * @return 值 
     */  
    public Object get(String key){
    	Jedis client = getJedis();
        try{
        	return key==null?null:client.get(key);  
        } catch (Exception e) {  
		    e.printStackTrace();  
		}finally {
			close(client);
		}
        return null;
    }  
      
    /** 
     * 普通缓存放入 永久有效
     * @param key 键 
     * @param value 值 
     * @return true成功 false失败 
     */  
    public boolean set(String key,String value) {
    	System.out.println("key："+key);
    	//System.out.println("value:"+value);
    	Jedis client = getJedis();
         try {  
         	//SetParams params = new SetParams();
         	client.set(key, value);
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }finally {
        	close(client);
		}
    	
    }  
      
    /** 
     * 普通缓存放入并设置时间 
     * @param key 键 
     * @param value 值 
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期 
     * @return true成功 false 失败 
     */  
    public boolean set(String key,String value,int expireTime){ 
    	Jedis client = getJedis();
        try {
        	SetParams params = new SetParams();
        	params.ex(expireTime);
        	client.set(key, value, params);
        	return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;  
        }finally {
        	close(client);
		}
    }    
    

    /** 
     * 设置 list 
     * @param <T> 
     * @param key 
     * @param value
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期  
     */  
    public <T> void setList(String key ,List<T> list,int expireTime){  
    	Jedis client = getJedis();
    	try { 
    		SetParams params = new SetParams();
    		params.ex(expireTime);
    		client.set(key.getBytes(),ObjectTranscoderUtil.serialize(list),params);  
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
        	close(client);
		} 
    }  
    /** 
     * 获取list 
     * @param <T> 
     * @param key 
     * @return list 
     */  
    public <T> List<T> getList(String key){  
    	Jedis client = getJedis(); 
    	try{
    		if(client == null || !client.exists(key.getBytes())){  
                return null;
            }  
            byte[] in = getJedis().get(key.getBytes());    
            List<T> list = (List<T>) ObjectTranscoderUtil.deserialize(in);    
            return list; 
    	} catch (Exception e) {  
		    e.printStackTrace();  
		}finally {
			close(client);
		}
    	return null;
    }
    
    /** 
     * 设置 map 
     * @param <T> 
     * @param key 
     * @param value 
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期 
     */  
    public <T> void setMap(String key ,Map<String,T> map,int expireTime){ 
    	Jedis client = getJedis(); 
        try {
        	SetParams params = new SetParams();
        	params.ex(expireTime);
        	client.set(key.getBytes(),ObjectTranscoderUtil.serialize(map),params);  
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
        	close(client);
		}  
    }
    
    /** 
     * 获取list 
     * @param <T> 
     * @param key 
     * @return list 
     */  
    public <T> Map<String,T> getMap(String key){  
    	Jedis client = getJedis(); 
    	try{
    		if(client == null || !client.exists(key.getBytes())){  
                return null;
            }  
            byte[] in = getJedis().get(key.getBytes());    
            Map<String,T> map = (Map<String, T>) ObjectTranscoderUtil.deserialize(in);    
            return map;  
    	} catch (Exception e) {  
		    e.printStackTrace();  
		}finally {
			close(client);
		}
    	return null;
    }  
    
    
    public <T> void setMapInt(String key ,Map<Integer,T> map,int expireTime){ 
    	Jedis client = getJedis(); 
        try {
        	SetParams params = new SetParams();
        	params.ex(expireTime);
        	client.set(key.getBytes(),ObjectTranscoderUtil.serialize(map),params);  
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
        	close(client);
		}  
    }
    public <T> Map<Integer,T> getMapInt(String key){  
    	Jedis client = getJedis(); 
    	try{
    		if(client == null || !client.exists(key.getBytes())){  
                return null;
            }  
            byte[] in = getJedis().get(key.getBytes());    
            Map<Integer,T> map = (Map<Integer, T>) ObjectTranscoderUtil.deserialize(in);    
            return map;  
    	} catch (Exception e) {  
		    e.printStackTrace();  
		}finally {
			close(client);
		}
    	return null;
    }  
}  