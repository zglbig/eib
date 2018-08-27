using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class DiceHistoryDto : SerializeMessage {
	[ProtoMember(1)]
	public List<DiceCountDto> diceCountDtos;
}
