using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class LotteryHistoryDto : SerializeMessage {
	[ProtoMember(1)]
	public long num;
	[ProtoMember(2)]
	public int result;
	[ProtoMember(3)]
	public int oddEnven;
	[ProtoMember(4)]
	public long lastTimeGrantAward;
	[ProtoMember(5)]
	public List<int> cardIds;
}
