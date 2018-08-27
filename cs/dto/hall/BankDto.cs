using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class BankDto : SerializeMessage {
	[ProtoMember(1)]
	public long exchangeGold;
	[ProtoMember(2)]
	public long holdGold;
	[ProtoMember(3)]
	public long bankResiduceGold;
}
