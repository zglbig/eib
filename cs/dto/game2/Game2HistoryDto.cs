using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class Game2HistoryDto : SerializeMessage {
	[ProtoMember(1)]
	public long count;
	[ProtoMember(2)]
	public bool one;
	[ProtoMember(3)]
	public bool two;
	[ProtoMember(4)]
	public bool three;
	[ProtoMember(5)]
	public bool four;
}
