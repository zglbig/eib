using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2PlayerRoomDto : SerializeMessage {
	[ProtoMember(1)]
	public Game2PositionPlayerInfoDto banker;
	[ProtoMember(2)]
	public int playerNum;
	[ProtoMember(3)]
	public int roomStatus;
	[ProtoMember(4)]
	public int roomTimer;
	[ProtoMember(5)]
	public int selfPosition;
	[ProtoMember(6)]
	public long betLimit;
	[ProtoMember(7)]
	public List<Game2PositionPlayerInfoDto> positionInfo;
}
