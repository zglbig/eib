using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class LoginDto : SerializeMessage {
	[ProtoMember(1)]
	public long uid;
	[ProtoMember(2)]
	public long gold;
	[ProtoMember(3)]
	public long diamond;
	[ProtoMember(4)]
	public long integral;
	[ProtoMember(5)]
	public string userName;
	[ProtoMember(6)]
	public string sex;
	[ProtoMember(7)]
	public int vipLv;
	[ProtoMember(8)]
	public long vipExp;
	[ProtoMember(9)]
	public string headImgUrl;
	[ProtoMember(10)]
	public bool isDialAward;
	[ProtoMember(11)]
	public int signInDialNum;
	[ProtoMember(12)]
	public bool isGiftAward;
	[ProtoMember(13)]
	public int signInDay;
	[ProtoMember(14)]
	public bool isSignIn;
	[ProtoMember(15)]
	public int useAutoId;
	[ProtoMember(16)]
	public string secretKey;
}
