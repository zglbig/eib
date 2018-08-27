using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2JackpotListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<Game2JackpotDto> jackpotList;
}
