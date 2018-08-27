using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GameRankingListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<GameRankingDto> listDto;
}
