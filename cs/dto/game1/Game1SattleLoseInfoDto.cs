using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game1SattleLoseInfoDto : SerializeMessage {
	[ProtoMember(1)]
	public long loseUid;
	[ProtoMember(2)]
	public long loseGold;
	[ProtoMember(3)]
	public long loseHoldGold;
	[ProtoMember(4)]
	public int loseCardType;
	[ProtoMember(5)]
	public List<int> loseCardIds;
}
