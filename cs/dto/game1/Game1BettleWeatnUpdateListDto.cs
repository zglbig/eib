using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game1BettleWeatnUpdateListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<Game1BettleWeatnUpdateDto> updateList;
}
