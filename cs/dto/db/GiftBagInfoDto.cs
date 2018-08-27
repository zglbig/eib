using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GiftBagInfoDto : SerializeMessage {
	[ProtoMember(1)]
	public int day;
	[ProtoMember(2)]
	public bool isDone;
	[ProtoMember(3)]
	public bool isAward;
}
