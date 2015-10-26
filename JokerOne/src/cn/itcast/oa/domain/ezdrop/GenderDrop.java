package cn.itcast.oa.domain.ezdrop;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.oa.domain.basemain.EasyUIDrop;


public class GenderDrop {

private GenderDrop() {
		
	}


	// 性别
	private static List<EasyUIDrop> genderList = genGenderList();
	
	public static List<EasyUIDrop> getGenderList() {
		return genderList;
	}

	private static List<EasyUIDrop> genGenderList() {
		List<EasyUIDrop> dropLs = new ArrayList<EasyUIDrop>();
		dropLs.add(new EasyUIDrop(1L + "", "男"));
		dropLs.add(new EasyUIDrop(2L + "", "女"));
		
		return dropLs;
	}

	
	// 性别赋值
	public static String retrieveGenderName(Long gender) {
			List<EasyUIDrop> dropLs = GenderDrop.getGenderList();
			for (EasyUIDrop drop : dropLs) {
				if (Long.valueOf(drop.getValue()).equals(gender)) {
					String genderName = drop.getText();
					return genderName;
			}
		}
		return null;
	}
	
}
