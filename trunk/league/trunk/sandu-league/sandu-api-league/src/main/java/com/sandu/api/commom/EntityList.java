
package com.sandu.api.commom;

import java.util.ArrayList;
import java.util.List;


public class EntityList<T> {
	private int totalCount;
	private List<T> dataList;
	private int start;
	private int limit;
	private boolean page = false;
	private boolean hasMore = false;
	
	public EntityList(){	
		this.dataList = new ArrayList<T>();
		this.totalCount = 0;
	}
	
	public EntityList(List<T> list){
//		this.totalCount = count;
		if(list == null) {
			this.dataList = new ArrayList<T>();
		} else {
			this.dataList = list ;
		}		
		this.totalCount = this.dataList.size();
	}
	
	public EntityList(int totalCount, int start, int limit, List<T> list){
		this.totalCount = totalCount;
		this.start = start;
		this.limit = limit;	
		this.dataList = list;
	}
	
	
	/**
	 * Suggest that restrict number of get back data through using start, limit, and slice query  
	 * rather than get back huge amount of data and use pagination feature of EntityList, that might 
	 * cause OOM(Out of Memory) problem.
	 * @param pagination
	 * @param start
	 * @param limit
	 */
	@Deprecated
	public EntityList(boolean pagination, int start, int limit) {
		this.page = pagination;
		this.start = start;
		this.limit = limit;		
	}	
	
	public EntityList(int totalCount, List<T> list) {
		this.totalCount = totalCount;
		this.dataList = list;
		
	}
	
	/**
	 * Suggest that restrict number of get back data through using start, limit, and slice query  
	 * rather than get back huge amount of data and use pagination feature of EntityList, that might 
	 * cause OOM(Out of Memory) problem.
	 * @param list
	 * @param start
	 * @param limit
	 */
	@Deprecated
	public EntityList(List<T> list, int start, int limit){
		this.start = start;
		this.limit = limit;
//		this.totalCount = count;
		if(list == null) {
			this.dataList = new ArrayList<T>();
		} else {
			this.dataList = list ;
		}		
		this.totalCount = this.dataList.size();
		this.page = true;
	}

	
	public int getTotalCount() {
		return this.totalCount;
	}	
	
	public void addEntity(T t){
		this.dataList.add(t);
	}
	
	public void enablePagination(){
		this.page = true;
	}
	
	/*public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}*/
	public List<T> getDataList() {
		if (page) {

			int lastIndex = start + limit;
			if (this.dataList.size() >= lastIndex) {
				return dataList.subList(start, lastIndex);
			} else if(this.dataList.size() >= start) { 
				return dataList.subList(start, dataList.size());
			} else {
				return new ArrayList<T>();
			}
		} else {
			return this.dataList;
		}
	}
	
	public void add(T t) {
		this.dataList.add(t);
		this.totalCount = this.dataList.size();
	}
	
	public List<T> getRawDataList(){
		return this.dataList ;
	}
	
	public int getLimit(){
		return this.limit ;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean hasMore() {
		return hasMore;
	}

	public void hasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	


    public int getStart() {
        return start;
    }

    public boolean isPage() {
        return page;
    }

    public boolean isHasMore() {
        return hasMore;
    }
}
