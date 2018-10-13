/**
 * 
 */
package com.cc.common.Utils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jay
 * @since 2016年12月26日
 */
public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	// Object序列化Json
	public static String ToJson(Object entity) throws  IOException {
		String entity2Json = objectMapper.writeValueAsString(entity);
		return entity2Json;
	}
	
	// Json反序列化Object
	@SuppressWarnings("unchecked")
	public static <E> E ToObject(String jsonString, Class<?> objectClass)throws IOException {
		E Class = (E) objectMapper.readValue(jsonString, objectClass);
		return Class;
	}
	
	// Entity带Date序列化时间格式化
	@SuppressWarnings({ "deprecation" })
	public static String objectWithTimeFormat(Object entity)
			throws  IOException {
		java.text.DateFormat myFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		objectMapper.setDateFormat(myFormat);
		String entity2Json = objectMapper.writeValueAsString(entity);
		return entity2Json;
	}

	// 另外一种序列化,对象转控制台输出
	public static void reseverObject(Object o) throws  IOException {
		JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
		jsonGenerator.writeObject(o);
	}

	// 反序列化
	@SuppressWarnings({ "unchecked" })
	public static <E> E serializerObject1(String jsonString, JavaType jt)
			throws  IOException {
		E Class =  objectMapper.readValue(jsonString, jt);
		return Class;
	}

	// 根据json字符串转化List和Map
	// 如果是Map类型
	// mapper.getTypeFactory().constructParametricType(HashMap.class,String.class,
	// Bean.class);
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	// json序列化map
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map serializerMap(String json) throws  IOException {
		Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
		maps = objectMapper.readValue(json, Map.class);
		return maps;
	}

	// 慢加载，根据json内节点名称获取节点内数据
	public static JsonNode slowSerializer(String json, String nodeName) throws IOException {

		JsonNode nodes = objectMapper.readTree(json); // 将Json串以树状结构读入内存
		JsonNode nodeContents = nodes.get(nodeName);// 得到results这个节点下的信息
		return nodeContents;
	}
	
	public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws Exception {
	       ObjectMapper mapper = new ObjectMapper();

	       JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

	       return mapper.readValue(jsonStr, javaType);

	}

}
