/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package com.lineage.server.templates;

/**
 * 娃娃合成调用图片系统
 * Manly by
 * @author Administrator
 *
 */
public class L1HeCheng {	

	private int _itemid;
	private String _gfxid;

	public L1HeCheng(int itemid, String gfxid) {
		_itemid = itemid;
		_gfxid = gfxid;
	}

	public int getItemid() {
		return _itemid;
	}
	public String getGfxid() {
		return _gfxid;
	}
}
