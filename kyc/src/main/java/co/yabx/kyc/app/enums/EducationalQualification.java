package co.yabx.kyc.app.enums;

public enum EducationalQualification {

	B_ARCH(3, "B.Arch"), B_DES(4, "B.Des"), B_E(5, "B.E/B.Tech"), B_PHARMA(6, "B.Pharma"), M_ARCH(7, "M.Arch"),
	M_DES(8, "M.Des"), M_E(9, "M.E/M.Tech"), M_PHARMA(10, "M.Pharma"), M_S_E(11, "M.S. (Engineering)"),
	B_IT(12, "B.IT"), BCA(13, "BCA"), MCA(14, "MCA"), PGDCA(15, "PGDCA"), B_COM(16, "B.Com"), CA(17, "CA"),
	CFA(18, "CFA"), CS(19, "CS"), ICWA(20, "ICWA"), M_COM(21, "M.Com"), BBA(22, "BBA"), BHM(23, "BHM"),
	MBA(24, "MBA/PGDM"), BAMS(25, "BAMS"), BDS(26, "BDS"), BHMS(27, "BHMS"), BPT(28, "BPT"), BVSC_(29, "BVSc."),
	DM(30, "DM"), M_D(31, "M.D."), M_S_M(32, "M.S. (Medicine)"), MBBS(33, "MBBS"), MCH(34, "MCh"), MDS(35, "MDS"),
	MPT(36, "MPT"), MVSC_(37, "MVSc."), BL_LLB(38, "BL/LLB"), ML_LLM(39, "ML/LLM"), B_A(40, "B.A"), B_ED(41, "B.Ed"),
	BFA(42, "BFA"), BJMC(43, "BJMC"), M_A(44, "M.A"), M_ED(45, "M.Ed"), M_SC(46, "M.Sc"), MFA(47, "MFA"),
	MJMC(48, "MJMC"), MSW(49, "MSW"), M_PHIL(50, "M.Phil"), PH_D(51, "Ph.D"), HIGH_SCHOOL(52, "High School"),
	TRADE_SCHOOL(53, "Trade School"), DIPLOMA(54, "Diploma"), OTHERS(55, "Others"), B_SC(56, "B.Sc");

	private int id;
	private String name;

	EducationalQualification(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public static EducationalQualification findById(int id) {
		EducationalQualification[] customEducations = values();
		for (EducationalQualification customEducation : customEducations) {
			if (customEducation.getId() == id) {
				return customEducation;
			}
		}
		return null;
	}

	public static EducationalQualification findByValue(String value) {
		EducationalQualification[] customEducations = values();
		for (EducationalQualification customEducation : customEducations) {
			if (customEducation.getName().equalsIgnoreCase(value)) {
				return customEducation;
			}
		}
		return null;
	}
}
