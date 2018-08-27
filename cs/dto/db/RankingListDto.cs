using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class RankingListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<RankingBaseDto> list;
}
