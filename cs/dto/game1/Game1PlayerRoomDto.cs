using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game1PlayerRoomDto : SerializeMessage {
	[ProtoMember(1)]
	public int roomId;
	[ProtoMember(2)]
	public int roomState;
	[ProtoMember(3)]
	public int selfPosition;
	[ProtoMember(4)]
	public int exchangeCardCount;
	[ProtoMember(5)]
	public List<Game1PlayerRoomBaseInfoDto> players;
}
