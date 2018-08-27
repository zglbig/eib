using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game1SattleEndDto : SerializeMessage {
	[ProtoMember(1)]
	public long winPlayerUid;
	[ProtoMember(2)]
	public long winGold;
	[ProtoMember(3)]
	public int winCardType;
	[ProtoMember(4)]
	public long winPlayerHoldGold;
	[ProtoMember(5)]
	public List<int> winCardId;
	[ProtoMember(6)]
	public List<Game1SattleLoseInfoDto> loseInfo;
}
