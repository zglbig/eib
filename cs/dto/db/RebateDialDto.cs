using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class RebateDialDto : SerializeMessage {
	[ProtoMember(1)]
	public int position;
	[ProtoMember(2)]
	public int awardId;
	[ProtoMember(3)]
	public List<ItemDto> items;
}
