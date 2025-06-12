package com.darkcoders.platform.configuration.multilingual.wrapper;

import com.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * The type Multi lingual filter wrapper.
 *
 * @author VisionWaves
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MultiLingualFilterWrapper {

	@Valid
	private List<CriteriaFilterParameter> filters;
	/**
	 * The Projection list.
	 */
	private List<String> projectionList;
	/**
	 * The Lower limit.
	 */
	@NotNull
	@Size(min = 1)
	private Integer lowerLimit;

	/**
	 * The Up limit.
	 */
	@NotNull
	@Size(max = 500)
	private Integer upLimit;

	/**
	 * The Order by column.
	 */
	private String orderByColumn;
	/**
	 * The Order type.
	 */
	private String orderType;

	/**
	 * The Lingual key.
	 */
	@NotBlank
	private String lingualKey;
	/**
	 * The Convert language type.
	 */
	private String convertLanguageType;
	/**
	 * The Converted language.
	 */
	private String convertedLanguage;

	public MultiLingualFilterWrapper() {
		super();
	}

	public MultiLingualFilterWrapper(List<CriteriaFilterParameter> filters, List<String> projectionList,
			Integer upLimit, Integer lowerLimit, String orderByColumn, String orderType,
			String convertedLanguage, String convertLanguageType) {
		this.filters = filters;
		this.projectionList = projectionList;
		this.upLimit = upLimit;
		this.lowerLimit = lowerLimit;
		this.orderByColumn = orderByColumn;
		this.orderType = orderType;
		this.convertedLanguage = convertedLanguage;
		this.convertLanguageType = convertLanguageType;
	}

	// Explicit getter methods
	public List<CriteriaFilterParameter> getFilters() {
		return filters;
	}

	public List<String> getProjectionList() {
		return projectionList;
	}

	public Integer getLowerLimit() {
		return lowerLimit;
	}

	public Integer getUpLimit() {
		return upLimit;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getLingualKey() {
		return lingualKey;
	}

	public String getConvertLanguageType() {
		return convertLanguageType;
	}

	public String getConvertedLanguage() {
		return convertedLanguage;
	}
} 