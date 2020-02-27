package editora3.facade;

import java.io.Serializable;
import java.util.Map;

import org.primefaces.model.SortOrder;

public class FiltrosLazyDataModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3896653425702623391L;

	int first;
	int pageSize;
    public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	 
	 
	public Map<String, Object> getFilters() {
		return filters;
	}
	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
	public FiltrosLazyDataModel(int first, int pageSize, String sortField, SortOrder sortOrder, 
			Map<String, Object> filters) {
		super();
		this.first = first;
		this.pageSize = pageSize;
		this.sortField = sortField;
	 	this.sortOrder=sortOrder;
		this.filters = filters;
	}
	public SortOrder getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	private String  sortField;
    private SortOrder sortOrder;
    private Map<String, Object> filters;
}
