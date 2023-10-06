package com.lineage.server.model;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.templates.L1HouseLocTmp;
import com.lineage.server.types.Point;

/**
 * 小屋座標相關資料
 * @author dexc
 *
 */
public class L1HouseLocation {

	private static final Log _log = LogFactory.getLog(L1HouseLocation.class);

	private static final int[] TELEPORT_LOC_MAPID = { 4, 4, 4, 350, };

	private static final Point[] TELEPORT_LOC_GIRAN = {
		new Point(33419, 32810), new Point(33343, 32723), // 倉庫、ペット保管所
		new Point(33553, 32712), new Point(32702, 32842), }; // 贖罪の使者、ギラン市場

	private static final Point[] TELEPORT_LOC_HEINE = {
		new Point(33604, 33236), new Point(33649, 33413), // 倉庫、ペット保管所
		new Point(33553, 32712), new Point(32702, 32842), }; // 贖罪の使者、ギラン市場

	private static final Point[] TELEPORT_LOC_ADEN = { new Point(33966, 33253),
		new Point(33921, 33177), // 倉庫、ペット保管所
		new Point(33553, 32712), new Point(32702, 32842), }; // 贖罪の使者、ギラン市場

	private static final Point[] TELEPORT_LOC_GLUDIN = {
		new Point(32628, 32807), new Point(32623, 32729), // 倉庫、ペット保管所
		new Point(33553, 32712), new Point(32702, 32842), }; // 贖罪の使者、ギラン市場

	// 小屋編號
	//private static final List<Integer> _houseIds = new ArrayList<Integer>();

	// 小屋座標資料(小屋編號/座標資料)
	private static final Map<Integer, L1HouseLocTmp> _houseLoc = new HashMap<Integer, L1HouseLocTmp>();

	/*public static void add(final Integer e) {
		_houseIds.add(e);
	}*/

	public static void put(final Integer e, final L1HouseLocTmp loc) {
		_houseLoc.put(e, loc);
	}

	private L1HouseLocation() {
	}

	/**
	 * 地下盟屋座標
	 * @param mapid
	 * @return
	 */
	public static boolean isInHouse(final short mapid) {
		switch (mapid) {
		case 5001:// 亞丁地下盟屋
		case 5002:// 亞丁地下盟屋
		case 5003:// 亞丁地下盟屋
		case 5004:// 亞丁地下盟屋
		case 5005:// 亞丁地下盟屋
		case 5006:// 亞丁地下盟屋
		case 5007:// 亞丁地下盟屋
		case 5008:// 亞丁地下盟屋
		case 5009:// 亞丁地下盟屋
		case 5010:// 亞丁地下盟屋
		case 5011:// 亞丁地下盟屋
		case 5012:// 亞丁地下盟屋
		case 5013:// 亞丁地下盟屋
		case 5014:// 亞丁地下盟屋
		case 5015:// 亞丁地下盟屋
		case 5016:// 亞丁地下盟屋
		case 5017:// 亞丁地下盟屋
		case 5018:// 亞丁地下盟屋
		case 5019:// 亞丁地下盟屋
		case 5020:// 亞丁地下盟屋
		case 5021:// 亞丁地下盟屋
		case 5022:// 亞丁地下盟屋
		case 5023:// 亞丁地下盟屋
		case 5024:// 亞丁地下盟屋
		case 5025:// 亞丁地下盟屋
		case 5026:// 亞丁地下盟屋
		case 5027:// 亞丁地下盟屋
		case 5028:// 亞丁地下盟屋
		case 5029:// 亞丁地下盟屋
		case 5030:// 亞丁地下盟屋
		case 5031:// 亞丁地下盟屋
		case 5032:// 亞丁地下盟屋
		case 5033:// 亞丁地下盟屋
		case 5034:// 亞丁地下盟屋
		case 5035:// 亞丁地下盟屋
		case 5036:// 亞丁地下盟屋
		case 5037:// 亞丁地下盟屋
		case 5038:// 亞丁地下盟屋
		case 5039:// 亞丁地下盟屋
		case 5040:// 亞丁地下盟屋
		case 5041:// 亞丁地下盟屋
		case 5042:// 亞丁地下盟屋
		case 5043:// 亞丁地下盟屋
		case 5044:// 亞丁地下盟屋
		case 5045:// 亞丁地下盟屋
		case 5046:// 亞丁地下盟屋
		case 5047:// 亞丁地下盟屋
		case 5048:// 亞丁地下盟屋
		case 5049:// 亞丁地下盟屋
		case 5050:// 亞丁地下盟屋
		case 5051:// 亞丁地下盟屋
		case 5052:// 亞丁地下盟屋
		case 5053:// 亞丁地下盟屋
		case 5054:// 亞丁地下盟屋
		case 5055:// 亞丁地下盟屋
		case 5056:// 亞丁地下盟屋
		case 5057:// 亞丁地下盟屋
		case 5058:// 亞丁地下盟屋
		case 5059:// 亞丁地下盟屋
		case 5060:// 亞丁地下盟屋
		case 5061:// 亞丁地下盟屋
		case 5062:// 亞丁地下盟屋
		case 5063:// 亞丁地下盟屋
		case 5064:// 亞丁地下盟屋
		case 5065:// 亞丁地下盟屋
		case 5066:// 亞丁地下盟屋
		case 5067:// 亞丁地下盟屋
		case 5068:// 奇岩地下盟屋
		case 5069:// 奇岩地下盟屋
		case 5070:// 奇岩地下盟屋
		case 5071:// 奇岩地下盟屋
		case 5072:// 奇岩地下盟屋
		case 5073:// 奇岩地下盟屋
		case 5074:// 奇岩地下盟屋
		case 5075:// 奇岩地下盟屋
		case 5076:// 奇岩地下盟屋
		case 5077:// 奇岩地下盟屋
		case 5078:// 奇岩地下盟屋
		case 5079:// 奇岩地下盟屋
		case 5080:// 奇岩地下盟屋
		case 5081:// 奇岩地下盟屋
		case 5082:// 奇岩地下盟屋
		case 5083:// 奇岩地下盟屋
		case 5084:// 奇岩地下盟屋
		case 5085:// 奇岩地下盟屋
		case 5086:// 奇岩地下盟屋
		case 5087:// 奇岩地下盟屋
		case 5088:// 奇岩地下盟屋
		case 5089:// 奇岩地下盟屋
		case 5090:// 奇岩地下盟屋
		case 5091:// 奇岩地下盟屋
		case 5092:// 奇岩地下盟屋
		case 5093:// 奇岩地下盟屋
		case 5094:// 奇岩地下盟屋
		case 5095:// 奇岩地下盟屋
		case 5096:// 奇岩地下盟屋
		case 5097:// 奇岩地下盟屋
		case 5098:// 奇岩地下盟屋
		case 5099:// 奇岩地下盟屋
		case 5100:// 奇岩地下盟屋
		case 5101:// 奇岩地下盟屋
		case 5102:// 奇岩地下盟屋
		case 5103:// 奇岩地下盟屋
		case 5104:// 奇岩地下盟屋
		case 5105:// 奇岩地下盟屋
		case 5106:// 奇岩地下盟屋
		case 5107:// 奇岩地下盟屋
		case 5108:// 奇岩地下盟屋
		case 5109:// 奇岩地下盟屋
		case 5110:// 奇岩地下盟屋
		case 5111:// 奇岩地下盟屋
		case 5112:// 奇岩地下盟屋
		case 5113:// 海音地下盟屋
		case 5114:// 海音地下盟屋
		case 5115:// 海音地下盟屋
		case 5116:// 海音地下盟屋
		case 5117:// 海音地下盟屋
		case 5118:// 海音地下盟屋
		case 5119:// 海音地下盟屋
		case 5120:// 海音地下盟屋
		case 5121:// 海音地下盟屋
		case 5122:// 海音地下盟屋
		case 5123:// 海音地下盟屋
			return true;
		}
		return false;
	}
	
	/**
	 * 指定座標是否屬於小屋範圍
	 *
	 * @param cha
	 * @return
	 */
	public static boolean isInHouse(final int locx, final int locy, final short mapid) {
		for (final Integer houseId : _houseLoc.keySet()) {
			if (isInHouseLoc(houseId, locx, locy, mapid)) {
				return true;
			}
		}

		/*for (final Integer houseId : _houseIds) {
			if (isInHouseLoc(houseId, locx, locy, mapid)) {
				return true;
			}
		}*/
		return false;
	}

	/**
	 * 指定位置是否在指定小屋範圍內
	 *
	 * @param houseId
	 * @param cha
	 * @return
	 */
	public static boolean isInHouseLoc(final int houseId, final int locx, final int locy, final short mapid) {
		try {
			final L1HouseLocTmp loc = _houseLoc.get(new Integer(houseId));
			if (loc != null) {
				int locx1 = loc.get_locx1();
				int locx2 = loc.get_locx2();
				int locy1 = loc.get_locy1();
				int locy2 = loc.get_locy2();
				int locx3 = loc.get_locx3();
				int locx4 = loc.get_locx4();
				int locy3 = loc.get_locy3();
				int locy4 = loc.get_locy4();
				
				int locmapid = loc.get_mapid();
				int basement = loc.get_basement();
				
				if ((locx >= locx1) && 
						(locx <= locx2) && 
						(locy >= locy1) && 
						(locy <= locy2) && 
						(mapid == locmapid)) {
					return true;
				}
				
				if (locx3 != 0) {
					if ((locx >= locx3) && 
							(locx <= locx4) && 
							(locy >= locy3) && 
							(locy <= locy4) && 
							(mapid == locmapid)) {
						return true;
					}
				}
				
				if (basement != 0) {
					if (mapid == basement) {
						return true;
					}
				}
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		}
		return false;		
	}

	/**
	 * 小屋座標
	 * @param houseId
	 * @return
	 */
	public static int[] getHouseLoc(final int houseId) { // houseIdからアジトの座標を返す
		final int[] loc = new int[3];
		try {
			L1HouseLocTmp locTmp = _houseLoc.get(new Integer(houseId));
			if (loc != null) {
				loc[0] = locTmp.get_homelocx();
				loc[1] = locTmp.get_homelocy();
				loc[2] = locTmp.get_mapid();
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		}
		return loc;
	}

	/**
	 * 小屋所屬地下盟屋座標
	 * @param houseId
	 * @return
	 */
	public static int[] getBasementLoc(final int houseId) { // houseIdからアジトの地下室の座標を返す
		int[] loc = new int[3];
		if ((houseId >= 262145) && (houseId <= 262189)) { // 奇岩血盟小屋1~45
			loc[0] = 32766;//7.6座標
			loc[1] = 32832;//7.6座標
			loc[2] = houseId - 257077;
			
		} else if ((houseId >= 327681) && (houseId <= 327691)) { // 海音血盟小屋1~11
			loc[0] = 32766;//7.6座標
			loc[1] = 32829;//7.6座標
			loc[2] = houseId - 322568;
			
		} else if ((houseId >= 524289) && (houseId <= 524294)) { // 古魯丁血盟小屋1~6
			// 地下室がないため、アジトの入り口の座標を返す
			loc = getHouseLoc(houseId);
		}
		return loc;
	}

	/**
	 * 小屋所屬領地座標
	 * @param houseId
	 * @param number
	 * @return
	 */
	public static int[] getHouseTeleportLoc(final int houseId, final int number) { // houseIdからテレポート先の座標を返す
		final int[] loc = new int[3];
		if ((houseId >= 262145) && (houseId <= 262189)) { // ギランアジト
			loc[0] = TELEPORT_LOC_GIRAN[number].getX();
			loc[1] = TELEPORT_LOC_GIRAN[number].getY();
			loc[2] = TELEPORT_LOC_MAPID[number];

		} else if ((houseId >= 327681) && (houseId <= 327691)) { // ハイネアジト
			loc[0] = TELEPORT_LOC_HEINE[number].getX();
			loc[1] = TELEPORT_LOC_HEINE[number].getY();
			loc[2] = TELEPORT_LOC_MAPID[number];

		} else if ((houseId >= 458753) && (houseId <= 458819)) { // アデンアジト
			loc[0] = TELEPORT_LOC_ADEN[number].getX();
			loc[1] = TELEPORT_LOC_ADEN[number].getY();
			loc[2] = TELEPORT_LOC_MAPID[number];

		} else if ((houseId >= 524289) && (houseId <= 524294)) { // グルーディンアジト1~6
			loc[0] = TELEPORT_LOC_GLUDIN[number].getX();
			loc[1] = TELEPORT_LOC_GLUDIN[number].getY();
			loc[2] = TELEPORT_LOC_MAPID[number];
		}
		return loc;
	}

}
