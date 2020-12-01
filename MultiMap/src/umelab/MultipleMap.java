package umelab;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * MutipleMap is able to multiple values for one key. 
 * example is following: 
 * key1 => valueA 
 * key1 => valueB 
 * key1 => valueC 
 * key2 => valueD 
 * key3 => valueE
 * 
 * key1 => valueA, valueB, valueC 
 * key2 => valueC 
 * key3 => valueE
 * 
 * @author e.umeda
 */
public class MultipleMap {
    
    private static final int DEFAULT_MAX_SIZE = 100;

    private HashMap<Object, ArrayList<Object>> mapper = new HashMap<Object, ArrayList<Object>>();

    private int valueMaxSize;

    /**
     * Constructor
     */
    public MultipleMap(){
        valueMaxSize = DEFAULT_MAX_SIZE;
    }

    /**
     * 指定したkeyオブジェクトにvalueオブジェクトを保存する
     * 
     * restore value obj with associated key.
     * @param key key obj
     * @param val value obj
     */
    public void put(Object key, Object val) {
        int listSize = 0;
        //get Arraylist for key obj
        ArrayList<Object> list = (ArrayList<Object>) mapper.get(key);
        if (list == null) {
            list = new ArrayList<Object>();
        } 
        listSize = list.size();

        if (listSize > valueMaxSize) {
            throw new ArrayIndexOutOfBoundsException("Exceed max size of value object");
        }

        list.add(val);
        mapper.put(key, list);
    }

   /**
     * 指定したkeyオブジェクトにvalue配列オブジェクトを保存する
     * 
     * restore value objs with associated key.
     * @param key key obj
     * @param val value obj array
     */
    public void put(Object key, Object[] vals) {
        for (int i = 0; i < vals.length; i++) {
            put(key, vals[i]);
        }
    }

    /**
     * 指定したkeyに付随するvalueオブジェクト配列を返却する
     * 
     * return value object array with associated key
     * @param key key
     * @return value obj array
     */
    public Object[] get(Object key) {
        ArrayList<Object> list = null;
        if (mapper.containsKey(key)) {
            list = (ArrayList<Object>) mapper.get(key);
        }
        return list.toArray();
    }

    /**
     * 指定されたkeyにオブジェクトが存在しているかのブール値を返却する
     * 
     * return true if MultipleMap contains a mapping object for the specified key.
     * @param key
     * @return
     */
    public boolean containsKey(Object key) {
        return mapper.containsKey(key);
    }

    /**
     * 指定されたkeyに付随するオブジェクトを削除する
     * 
     * remove a mapping objects for the specified key from Multimap object.
     * @param key
     */
    public void remove(Object key) {
        mapper.remove(key);
    }

    /**
     * return number of keys in MultipleMap
     * @return
     */
    public int size() {
        return mapper.size();
    }

    /**
     * keyに格納可能なvalueオブジェクトサイズを設定する
     * set maximum size of restored value object
     * @param size  max size of value object
     */
    public void setValMaxSize(int size) {
        this.valueMaxSize = size;
    }

    /**
     * 指定したvalueオブジェクトに紐づくkeyオブジェクトを返却する
     * search key obj with specified value
     * @param value specifed value obj
     */
    public Object findKey(Object value) {
        Object obj = null;
        for(Object keyObj : mapper.keySet()) {
            ArrayList<Object> tmp_list = (ArrayList<Object>) mapper.get(keyObj);
            if (tmp_list == null || tmp_list.size() == 0) {
                return obj;
            }
            for (int i = 0; i < tmp_list.size(); i++) {
                Object o = (Object) tmp_list.get(i);
                if(o.equals(value)) {
                    return keyObj;
                }
            }
        }
        return obj;
    }
}
