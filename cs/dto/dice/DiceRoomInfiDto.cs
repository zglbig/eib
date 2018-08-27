using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class DiceRoomInfiDto : SerializeMessage {
	[ProtoMember(1)]
	public int betPlayerNum;
	[ProtoMember(2)]
	public long betAllNum;
	[ProtoMember(3)]
	public int roomNum;
	[ProtoMember(4)]
	public int roomStatus;
	[ProtoMember(5)]
	public int roomTimer;
	[ProtoMember(6)]
	public int selfPosition;
	[ProtoMember(7)]
	public long betLimit;
	[ProtoMember(8)]
	public List<GameDicePositionPlayerInfoDto> positionInfo;
}
