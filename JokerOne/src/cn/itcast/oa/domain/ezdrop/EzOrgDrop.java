package cn.itcast.oa.domain.ezdrop;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.oa.domain.basemain.EasyUIDrop;


public class EzOrgDrop {

private EzOrgDrop() {
		
	}


	// 组织机构属性
	private static List<EasyUIDrop> groupAttList = genGroupAttList();
	
	public static List<EasyUIDrop> getGroupAttList() {
		return groupAttList;
	}

	private static List<EasyUIDrop> genGroupAttList() {
		List<EasyUIDrop> dropLs = new ArrayList<EasyUIDrop>();
		dropLs.add(new EasyUIDrop(1L + "", "职能"));
		dropLs.add(new EasyUIDrop(2L + "", "生产"));
		dropLs.add(new EasyUIDrop(3L + "", "销售"));
		dropLs.add(new EasyUIDrop(4L + "", "技术"));
		dropLs.add(new EasyUIDrop(5L + "", "质量"));
		dropLs.add(new EasyUIDrop(6L + "", "其他"));
		return dropLs;
	}

	
	// 组织机构属性赋值
	public static String retrieveGroupAttName(Long groupAtt) {
			List<EasyUIDrop> dropLs = EzOrgDrop.getGroupAttList();
			for (EasyUIDrop drop : dropLs) {
				if (Long.valueOf(drop.getValue()).equals(groupAtt)) {
					String groupAttName = drop.getText();
					return groupAttName;
			}
		}
		return null;
	}
	
}
