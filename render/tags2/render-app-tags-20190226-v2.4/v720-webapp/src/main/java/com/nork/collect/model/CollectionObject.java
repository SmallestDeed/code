package com.nork.collect.model;

import java.io.Serializable;

public class CollectionObject implements Serializable {
	private String host;
	private String origin;
	private String uri;
	private String method;
	private String token;
	private String params;
	private String body;
	private String referfer;
	private String userAgent;
	private String remoteAddress;
	private Integer statusCode;
	private Long userId;
	private Long companyId;
	private Long startTime;
	private Long endTime;
	private String platform;
	private String response;

	public static CollectionObjectBuilder builder() {
		return new CollectionObjectBuilder();
	}

	public String getHost() {
		return this.host;
	}

	public String getOrigin() {
		return this.origin;
	}

	public String getUri() {
		return this.uri;
	}

	public String getMethod() {
		return this.method;
	}

	public String getToken() {
		return this.token;
	}

	public String getParams() {
		return this.params;
	}

	public String getBody() {
		return this.body;
	}

	public String getReferfer() {
		return this.referfer;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public String getRemoteAddress() {
		return this.remoteAddress;
	}

	public Integer getStatusCode() {
		return this.statusCode;
	}

	public Long getUserId() {
		return this.userId;
	}

	public Long getCompanyId() {
		return this.companyId;
	}

	public Long getStartTime() {
		return this.startTime;
	}

	public Long getEndTime() {
		return this.endTime;
	}

	public String getPlatform() {
		return this.platform;
	}

	public String getResponse() {
		return this.response;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setReferfer(String referfer) {
		this.referfer = referfer;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof CollectionObject))
			return false;
		CollectionObject other = (CollectionObject) o;
		if (!other.canEqual(this))
			return false;
		Object this$host = getHost();
		Object other$host = other.getHost();
		if (this$host == null ? other$host != null : !this$host.equals(other$host))
			return false;
		Object this$origin = getOrigin();
		Object other$origin = other.getOrigin();
		if (this$origin == null ? other$origin != null : !this$origin.equals(other$origin))
			return false;
		Object this$uri = getUri();
		Object other$uri = other.getUri();
		if (this$uri == null ? other$uri != null : !this$uri.equals(other$uri))
			return false;
		Object this$method = getMethod();
		Object other$method = other.getMethod();
		if (this$method == null ? other$method != null : !this$method.equals(other$method))
			return false;
		Object this$token = getToken();
		Object other$token = other.getToken();
		if (this$token == null ? other$token != null : !this$token.equals(other$token))
			return false;
		Object this$params = getParams();
		Object other$params = other.getParams();
		if (this$params == null ? other$params != null : !this$params.equals(other$params))
			return false;
		Object this$body = getBody();
		Object other$body = other.getBody();
		if (this$body == null ? other$body != null : !this$body.equals(other$body))
			return false;
		Object this$referfer = getReferfer();
		Object other$referfer = other.getReferfer();
		if (this$referfer == null ? other$referfer != null : !this$referfer.equals(other$referfer))
			return false;
		Object this$userAgent = getUserAgent();
		Object other$userAgent = other.getUserAgent();
		if (this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent))
			return false;
		Object this$remoteAddress = getRemoteAddress();
		Object other$remoteAddress = other.getRemoteAddress();
		if (this$remoteAddress == null ? other$remoteAddress != null : !this$remoteAddress.equals(other$remoteAddress))
			return false;
		Object this$statusCode = getStatusCode();
		Object other$statusCode = other.getStatusCode();
		if (this$statusCode == null ? other$statusCode != null : !this$statusCode.equals(other$statusCode))
			return false;
		Object this$userId = getUserId();
		Object other$userId = other.getUserId();
		if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId))
			return false;
		Object this$companyId = getCompanyId();
		Object other$companyId = other.getCompanyId();
		if (this$companyId == null ? other$companyId != null : !this$companyId.equals(other$companyId))
			return false;
		Object this$startTime = getStartTime();
		Object other$startTime = other.getStartTime();
		if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime))
			return false;
		Object this$endTime = getEndTime();
		Object other$endTime = other.getEndTime();
		if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime))
			return false;
		Object this$platform = getPlatform();
		Object other$platform = other.getPlatform();
		if (this$platform == null ? other$platform != null : !this$platform.equals(other$platform))
			return false;
		Object this$response = getResponse();
		Object other$response = other.getResponse();
		return this$response == null ? other$response == null : this$response.equals(other$response);
	}

	protected boolean canEqual(Object other) {
		return other instanceof CollectionObject;
	}

	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $host = getHost();
		result = result * 59 + ($host == null ? 43 : $host.hashCode());
		Object $origin = getOrigin();
		result = result * 59 + ($origin == null ? 43 : $origin.hashCode());
		Object $uri = getUri();
		result = result * 59 + ($uri == null ? 43 : $uri.hashCode());
		Object $method = getMethod();
		result = result * 59 + ($method == null ? 43 : $method.hashCode());
		Object $token = getToken();
		result = result * 59 + ($token == null ? 43 : $token.hashCode());
		Object $params = getParams();
		result = result * 59 + ($params == null ? 43 : $params.hashCode());
		Object $body = getBody();
		result = result * 59 + ($body == null ? 43 : $body.hashCode());
		Object $referfer = getReferfer();
		result = result * 59 + ($referfer == null ? 43 : $referfer.hashCode());
		Object $userAgent = getUserAgent();
		result = result * 59 + ($userAgent == null ? 43 : $userAgent.hashCode());
		Object $remoteAddress = getRemoteAddress();
		result = result * 59 + ($remoteAddress == null ? 43 : $remoteAddress.hashCode());
		Object $statusCode = getStatusCode();
		result = result * 59 + ($statusCode == null ? 43 : $statusCode.hashCode());
		Object $userId = getUserId();
		result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
		Object $companyId = getCompanyId();
		result = result * 59 + ($companyId == null ? 43 : $companyId.hashCode());
		Object $startTime = getStartTime();
		result = result * 59 + ($startTime == null ? 43 : $startTime.hashCode());
		Object $endTime = getEndTime();
		result = result * 59 + ($endTime == null ? 43 : $endTime.hashCode());
		Object $platform = getPlatform();
		result = result * 59 + ($platform == null ? 43 : $platform.hashCode());
		Object $response = getResponse();
		result = result * 59 + ($response == null ? 43 : $response.hashCode());
		return result;
	}

	public String toString() {
		return "CollectionObject(host=" + getHost() + ", origin=" + getOrigin() + ", uri=" + getUri() + ", method="
				+ getMethod() + ", token=" + getToken() + ", params=" + getParams() + ", body=" + getBody()
				+ ", referfer=" + getReferfer() + ", userAgent=" + getUserAgent() + ", remoteAddress="
				+ getRemoteAddress() + ", statusCode=" + getStatusCode() + ", userId=" + getUserId() + ", companyId="
				+ getCompanyId() + ", startTime=" + getStartTime() + ", endTime=" + getEndTime() + ", platform="
				+ getPlatform() + ", response=" + getResponse() + ")";
	}

	public CollectionObject() {
	}

	public CollectionObject(String host, String origin, String uri, String method, String token, String params,
			String body, String referfer, String userAgent, String remoteAddress, Integer statusCode, Long userId,
			Long companyId, Long startTime, Long endTime, String platform, String response) {
		this.host = host;
		this.origin = origin;
		this.uri = uri;
		this.method = method;
		this.token = token;
		this.params = params;
		this.body = body;
		this.referfer = referfer;
		this.userAgent = userAgent;
		this.remoteAddress = remoteAddress;
		this.statusCode = statusCode;
		this.userId = userId;
		this.companyId = companyId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.platform = platform;
		this.response = response;
	}

	public static class CollectionObjectBuilder {
		private String host;
		private String origin;
		private String uri;
		private String method;
		private String token;
		private String params;
		private String body;
		private String referfer;
		private String userAgent;
		private String remoteAddress;
		private Integer statusCode;
		private Long userId;
		private Long companyId;
		private Long startTime;
		private Long endTime;
		private String platform;
		private String response;

		public CollectionObjectBuilder host(String host) {
			this.host = host;
			return this;
		}

		public CollectionObjectBuilder origin(String origin) {
			this.origin = origin;
			return this;
		}

		public CollectionObjectBuilder uri(String uri) {
			this.uri = uri;
			return this;
		}

		public CollectionObjectBuilder method(String method) {
			this.method = method;
			return this;
		}

		public CollectionObjectBuilder token(String token) {
			this.token = token;
			return this;
		}

		public CollectionObjectBuilder params(String params) {
			this.params = params;
			return this;
		}

		public CollectionObjectBuilder body(String body) {
			this.body = body;
			return this;
		}

		public CollectionObjectBuilder referfer(String referfer) {
			this.referfer = referfer;
			return this;
		}

		public CollectionObjectBuilder userAgent(String userAgent) {
			this.userAgent = userAgent;
			return this;
		}

		public CollectionObjectBuilder remoteAddress(String remoteAddress) {
			this.remoteAddress = remoteAddress;
			return this;
		}

		public CollectionObjectBuilder statusCode(Integer statusCode) {
			this.statusCode = statusCode;
			return this;
		}

		public CollectionObjectBuilder userId(Long userId) {
			this.userId = userId;
			return this;
		}

		public CollectionObjectBuilder companyId(Long companyId) {
			this.companyId = companyId;
			return this;
		}

		public CollectionObjectBuilder startTime(Long startTime) {
			this.startTime = startTime;
			return this;
		}

		public CollectionObjectBuilder endTime(Long endTime) {
			this.endTime = endTime;
			return this;
		}

		public CollectionObjectBuilder platform(String platform) {
			this.platform = platform;
			return this;
		}

		public CollectionObjectBuilder response(String response) {
			this.response = response;
			return this;
		}

		public CollectionObject build() {
			return new CollectionObject(this.host, this.origin, this.uri, this.method, this.token, this.params,
					this.body, this.referfer, this.userAgent, this.remoteAddress, this.statusCode, this.userId,
					this.companyId, this.startTime, this.endTime, this.platform, this.response);
		}

		public String toString() {
			return "CollectionObject.CollectionObjectBuilder(host=" + this.host + ", origin=" + this.origin + ", uri="
					+ this.uri + ", method=" + this.method + ", token=" + this.token + ", params=" + this.params
					+ ", body=" + this.body + ", referfer=" + this.referfer + ", userAgent=" + this.userAgent
					+ ", remoteAddress=" + this.remoteAddress + ", statusCode=" + this.statusCode + ", userId="
					+ this.userId + ", companyId=" + this.companyId + ", startTime=" + this.startTime + ", endTime="
					+ this.endTime + ", platform=" + this.platform + ", response=" + this.response + ")";
		}

	}
}