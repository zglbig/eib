using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class ItemDto : SerializeMessage {
	[ProtoMember(1)]
	public int itemId;
	[ProtoMember(2)]
	public long itemCount;
}
