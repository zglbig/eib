using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GameDicePositionPlayerInfoListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<GameDicePositionPlayerInfoDto> playerList;
}
