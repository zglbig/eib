using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class LotteryRoomInfoDto : SerializeMessage {
	[ProtoMember(1)]
	public int playerNum;
	[ProtoMember(2)]
	public long lastTimeGrantAward;
	[ProtoMember(3)]
	public long nowBetMoney;
	[ProtoMember(4)]
	public int residueTime;
	[ProtoMember(5)]
	public int roomStatus;
	[ProtoMember(6)]
	public List<LotteryHistoryDto> historyDtos;
}
