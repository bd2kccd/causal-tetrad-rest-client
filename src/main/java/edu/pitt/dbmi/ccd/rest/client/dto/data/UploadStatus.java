/*
 * Copyright (C) 2015 University of Pittsburgh.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.pitt.dbmi.ccd.rest.client.dto.data;

/**
 *
 * Sep 22, 2015 12:59:11 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * 
 * Dec 15, 2015 12:55:27 PM
 * 
 * @author Chirayu (Kong) Wongchokprasitti
 *
 */
public class UploadStatus {

	private String id;

	private boolean paused;

	/**
	 * @param id
	 * @param paused
	 */
	public UploadStatus(String id, boolean paused) {
		this.id = id;
		this.paused = paused;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}
