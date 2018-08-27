using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class RedPacketDto : SerializeMessage {
	[ProtoMember(1)]
	public long residueGold;
	[ProtoMember(2)]
	public long reduceGold;
	[ProtoMember(3)]
	public string friendUserName;
}
