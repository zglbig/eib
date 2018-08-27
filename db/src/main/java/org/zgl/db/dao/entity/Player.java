package org.zgl.db.dao.entity;
import org.zgl.MD5;
import org.zgl.db.dao.mapper.FriendsMapper;
import org.zgl.db.utils.SpringUtils;
import org.zgl.dto.clinet.commond.BasePlayerDto;
import org.zgl.dto.clinet.db.LoginDto;
import org.zgl.dto.server.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Player {
	private Integer id;
	private Long uid;
	private String account;
	private String password;
	private String userName;
	private String headImgUrl;
	private String gender;
	/**推广人的uid*/
	private Long generalizeUid;
	private String createTime;
	private String lastEditTime;
	/**常用地址*/
	private String addr;
	private Weath weath;
	private Sigin sigin;
	private Prop prop;
	private List<Friends> friends;
	private List<Autos> autos;
	private GiftBag giftBag;
	private Task task;
	/**返利轮盘*/
	private RebateDial rebateDial;
	/**签名*/
	private String signature;
	/**联系方式*/
	private String contactWay;
	/**扩展对象 json*/
	private String extend;
	private List<String> scenesIds;
	/**密钥持有 每次登陆都不一样*/
	private String secretKey;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public RebateDial getRebateDial() {
		return rebateDial;
	}

	public void setRebateDial(RebateDial rebateDial) {
		this.rebateDial = rebateDial;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getGeneralizeUid() {
		return generalizeUid;
	}

	public void setGeneralizeUid(Long generalizeUid) {
		this.generalizeUid = generalizeUid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public List<Friends> getFriends() {
		return friends;
	}

	public void setFriends(List<Friends> friends) {
		this.friends = friends;
	}

	public GiftBag getGiftBag() {
		return giftBag;
	}

	public void setGiftBag(GiftBag giftBag) {
		this.giftBag = giftBag;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(String lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Sigin getSigin() {
		return sigin;
	}

	public void setSigin(Sigin sigin) {
		this.sigin = sigin;
	}

	public List<Autos> getAutos() {
		return autos;
	}

	public void setAutos(List<Autos> autos) {
		this.autos = autos;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Prop getProp() {
		return prop;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setProp(Prop prop) {
		this.prop = prop;
	}

	public List<String> getScenesIds() {
		if(this.scenesIds == null){
			this.scenesIds = new ArrayList<>(3);
		}
		return scenesIds;
	}

	public void setScenesIds(List<String> scenesIds) {
		this.scenesIds = scenesIds;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Weath getWeath() {
		if(this.weath == null){
			this.weath = new Weath();
		}
		return weath;
	}

	public void setWeath(Weath weath) {
		this.weath = weath;
	}

	public LoginDto getLoginDto(){
	    LoginDto dto = new LoginDto();
	    dto.setUid(this.uid);
	    dto.setUserName(this.userName);
	    dto.setHeadImgUrl(this.headImgUrl);
	    dto.setSex(this.gender);
	    dto.setGold(weath.getGold());
	    dto.setDiamond(weath.getDiamond());
	    dto.setIntegral(weath.getIntegral());
	    dto.setVipLv(weath.getVipLv());
	    dto.setVipExp(weath.getVipExp());
	    dto.setUseAutoId(weath.getUseAutoId());
	    this.secretKey = MD5.wxMd5(System.currentTimeMillis()+"");
	    dto.setSecretKey(this.secretKey);
	    return dto;
    }
    public Player getPlayer(String account, String password,String userName, String headImgUrl, String gender,String ip){
		this.account = account;
		this.password = password;
		this.headImgUrl = headImgUrl;
		this.gender = gender;
		this.userName = userName;
		this.addr = ip;
		return this;
	}
	public Game1ServerPlayerDto game1PlayerInfo(){
		Game1ServerPlayerDto info = new Game1ServerPlayerDto();
		info.setUid(this.uid);
		info.setUserName(this.userName);
		info.setSex(this.gender);
		info.setGold(this.weath.getGold());
		info.setHeadImg(this.headImgUrl);
		info.setUseAutoId(this.weath.getUseAutoId());
		info.setVipLv(this.weath.getVipLv());
		return info;
	}
	public GateServerPlayerDto gatePlayerInfo(){
		GateServerPlayerDto dto = new GateServerPlayerDto(this.uid,this.userName,this.headImgUrl,this.weath.getVipLv(),this.secretKey,this.gender);
		return dto;
	}
	public Game2ServerPlayerDto game2ServerPlayerInfo(){
		Game2ServerPlayerDto dto = new Game2ServerPlayerDto();
		dto.setUid(this.getUid());
		dto.setUserName(this.getUserName());
		dto.setHeadImg(this.headImgUrl);
		dto.setUseAutoId(this.weath.getUseAutoId());
		dto.setVipLv(this.weath.getVipLv());
		dto.setGold(this.weath.getGold());
		dto.setSex(this.gender);
		dto.setKickingCardNum(this.prop.getKickingCard());
		return dto;
	}
	public GameDiceServerPlayerDto gameDiceServerPlayerInfo(){
		GameDiceServerPlayerDto dto = new GameDiceServerPlayerDto();
		dto.setUid(this.getUid());
		dto.setUserName(this.getUserName());
		dto.setHeadImg(this.headImgUrl);
		dto.setUseAutoId(this.weath.getUseAutoId());
		dto.setVipLv(this.weath.getVipLv());
		dto.setGold(this.weath.getGold());
		dto.setSex(this.gender);
		dto.setKickingCardNum(this.prop.getKickingCard());
		return dto;
	}
	public HallServerPlayerDto hallServerPlayerInfo(){
		HallServerPlayerDto dto = new HallServerPlayerDto();
		dto.setUid(this.uid);
		dto.setUserName(this.userName);
		dto.setSex(this.gender);
		dto.setHeadImgUrl(this.headImgUrl);
		if(this.friends == null){
			FriendsMapper mapper = SpringUtils.getBean(FriendsMapper.class);
			List<Friends> friends1 = mapper.queryFriendsUid(uid);
			this.friends = friends1;
		}
		List<BasePlayerDto> basePlayerDtos = new ArrayList<>();
		if(this.friends != null){
			Iterator<Friends> iterator = this.friends.iterator();
			while (iterator.hasNext()){
				Friends f = iterator.next();
				basePlayerDtos.add(f.basePlayer());
			}
		}
		dto.setFriends(basePlayerDtos);
		dto.setVipLv(this.weath.getVipLv());
		dto.setGold(this.weath.getGold());
		dto.setFlower(this.prop.getFlower());
		dto.setEgg(this.prop.getEgg());
		dto.setBomb(this.prop.getBomb());
		dto.setSportsCar(this.prop.getSportsCar());
		dto.setAircraft(this.prop.getAircraft());
		dto.setStreamer(this.prop.getStreamer());
		dto.setTower(this.prop.getTower());
		dto.setDiamondRing(this.prop.getDiamondRing());
		dto.setBlueSuccubus(this.prop.getBlueSuccubus());
		return dto;
	}
}
