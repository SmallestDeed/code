
package com.sandu.api.commom;

import java.util.ArrayList;
import java.util.List;

/**
 * Define a generic super class for all data type in return JsonListModel
 * @author ling.wang
 *
 * @param <T>
 */
public class JsonListData<T> {
	
	protected Long totalCount = 0l; // this is only valid when data.list is not empty
	
	protected Long rawDataTotalCount = 0l; // this is the value that before filter by backend.
	
	protected boolean hasMore = false; // this is only valid when data.list is not empty
	
	protected List<T> list;
	
	public JsonListData(List<T> list) {
		super();
		if(list == null){
			list = new ArrayList<T>();
		}
		this.totalCount = Long.valueOf(list.size());
		this.hasMore = false;
		this.list = list;
	}
	
	public JsonListData(long totalCount, boolean hasMore, List<T> list) {
		super();
		if(list == null){
			list = new ArrayList<T>();
		}
		this.totalCount = totalCount;
		this.hasMore = hasMore;
		this.list = list;
	}
	
	public JsonListData(long totalCount, long rawDataTotalCount, boolean hasMore, List<T> list) {
		super();
		if(list == null){
			list = new ArrayList<T>();
		}
		this.totalCount = totalCount;
		this.rawDataTotalCount = rawDataTotalCount;
		this.hasMore = hasMore;
		this.list = list;
	}
	
	public long getTotalCount() {
		return totalCount;
	}

	public Long getRawDataTotalCount() {
		return rawDataTotalCount;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public List<T> getList() {
		return this.list;
	}
}
