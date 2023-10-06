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
package com.lineage.server.Manly;
/**
 * 装备溶解系统
 * BY Manly
 * @author Administrator
 *
 */

public class L1WenYang {


	private int _type;//類型
	
	private int _level;//等級
	
	private String _not;//说明
	
	private int _liliang;//力量
	
	private int _minjie;//敏捷
	
	private int _zhili;//智力
	
	private int _jingshen;//精神
	
	private int _tizhi;//体质
	
	private int _meili;//魅力
	
	private int _xue;//HP
	
	private int _mo;//MP
	
	private int _huixue;//HPR
	
	private int _huimo;//MPR
	
	private int _ewai;//攻击额外
	
	private int _chenggong;//攻击成功
	
	private int _mogong;//SP
	
	private int _mofang;//MR
	
	private int _feng;//风属性防御
	
	private int _shui;//水属性防御
	
	private int _tu;//地属性防御
	
	private int _huo;//火属性防御
	
	private int _jianmian;//伤害减免
	
	private int _jingyan;//经验
	
	

	public L1WenYang(int type,int level,String not,int liliang ,
			int minjie,int zhili,int jingshen,int tizhi,int meili,int xue,int mo
			,int huixue,int huimo,int ewai,int chenggong,int mogong,int mofang
			,int feng,int shui,int tu,int huo,int jianmian,int jingyan) {
        _type = type;
		_level = level;
		_not = not;
		_liliang = liliang;
		_minjie = minjie;
		_zhili =zhili;
		_jingshen = jingshen;
		_tizhi = tizhi;
		_meili = meili;
		_xue = xue;
		_mo = mo;
		_huixue = huixue;
		_huimo = huimo;
		_ewai = ewai;
		_chenggong  = chenggong;
		_mogong = mogong;
		_mofang = mofang;
		_feng = feng;
		_shui = shui;
		_tu = tu;
		_huo = huo;
		_jianmian = jianmian;
		_jingyan = jingyan;

	}

	public int getType() {
		return _type;
	}
	
	public int getLevel() {
		return _level;
	}
	
	public String getNot() {
		return _not;
	}
	
	public int getliliang() {
		return _liliang;
	}
	
	public int getminjie() {
		return _minjie;
	}
	
	public int getzhili() {
		return _zhili;
	}
	
	public int getjingshen() {
		return _jingshen;
	}
	
	public int gettizhi() {
		return _tizhi;
	}
	
	public int getmeili() {
		return _meili;
	}
	
	public int getxue() {
		return _xue;
	}
	
	public int getmo() {
		return _mo;
	}
	
	public int gethuixue() {
		return _huixue;
	}
	
	public int gethuimo() {
		return _huimo;
	}
	
	public int getewai() {
		return _ewai;
	}
	
	public int getchenggong() {
		return _chenggong;
	}
	
	public int getmogong() {
		return _mogong;
	}
	
	public int getmofang() {
		return _mofang;
	}
	
	public int getfeng() {
		return _feng;
	}
	
	public int getshui() {
		return _shui;
	}
	
	public int gettu() {
		return _tu;
	}
	
	public int gethuo() {
		return _huo;
	}
	
	public int getjianmian() {
		return _jianmian;
	}
	
	public int getjingyan() {
		return _jingyan;
	}
}
