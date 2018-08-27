using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GoldBaseDto : SerializeMessage {
	[ProtoMember(1)]
	public long exchangeGold;
	[ProtoMember(2)]
	public long holdGold;
}
