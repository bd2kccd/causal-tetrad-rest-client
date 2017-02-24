package edu.pitt.dbmi.ccd.rest.client.dto.user;

import java.util.Date;

/**
 * 
 * Oct 18, 2016 3:45:11 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti, PhD
 * 
 */
public class JsonWebToken {
    
    private int userId;
    
    private String jwt;
    
    private Date issuedTime;
    
    private long lifetime;
    
    private Date expireTime;
    
    private String[] wallTime;
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Date getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(Date issuedTime) {
        this.issuedTime = issuedTime;
    }

    public long getLifetime() {
        return lifetime;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

	public String[] getWallTime() {
		return wallTime;
	}

	public void setWallTime(String[] wallTime) {
		this.wallTime = wallTime;
	}

}
