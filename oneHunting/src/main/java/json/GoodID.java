package json;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoodID {
	
	@JsonProperty("goodID")
	private Set<String> ids = new HashSet<>();
	
	public Set<String> getIds() {
		return this.ids;
		
	}
	
	public void setIds(Set<String> ids) {
        this.ids = ids;
	}
	
	/**
	 * SETに値を追加する
	 */
	public void addIds(String Id) {
		ids.add(Id);
	}
	
	/**
	 * SETの値を削除する
	 */
	public void removeIds(String Id) {
		ids.remove(Id);
	}
	
}
