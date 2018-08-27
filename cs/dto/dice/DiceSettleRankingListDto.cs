using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class DiceSettleRankingListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<DiceSettleRankingDto> dtos;
}
