package json;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoodID {
	
	
	private Set<String> ids = new HashSet<>();
	
	@JsonProperty("goodID")
	public Set<String> getIds() {
		return this.ids;
		
	}
	
	@JsonProperty("goodID")
	public void setIds(Set<String> ids) {
        this.ids = ids;
	}
	
	/**
	 * SETに値を追加する
	 */
	public void addIds(String Id) {
		ids.add(Id);
	}
	
	
}
